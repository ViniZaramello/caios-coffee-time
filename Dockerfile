# Multi-stage build para otimizar o tamanho da imagem final
FROM gradle:8.5-jdk17 AS builder

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos de configuração do Gradle
COPY gradle gradle
COPY gradlew .
COPY gradlew.bat .
COPY gradle.properties .
COPY settings.gradle.kts .
COPY build.gradle.kts .

# Copia os módulos necessários
COPY shared shared
COPY composeApp composeApp

# Build do projeto wasmJs (gera os arquivos estáticos)
RUN chmod +x ./gradlew && \
    ./gradlew :composeApp:wasmJsBrowserDistribution --no-daemon

# Stage 2: Servidor web leve para servir os arquivos estáticos
FROM nginx:alpine

# Copia os arquivos buildados do stage anterior
COPY --from=builder /app/composeApp/build/dist/wasmJs/productionExecutable /usr/share/nginx/html

# Cria configuração customizada do nginx
RUN echo 'server {' > /etc/nginx/conf.d/default.conf && \
    echo '    listen 80;' >> /etc/nginx/conf.d/default.conf && \
    echo '    server_name localhost;' >> /etc/nginx/conf.d/default.conf && \
    echo '    root /usr/share/nginx/html;' >> /etc/nginx/conf.d/default.conf && \
    echo '    index index.html;' >> /etc/nginx/conf.d/default.conf && \
    echo '' >> /etc/nginx/conf.d/default.conf && \
    echo '    # Compressão Gzip' >> /etc/nginx/conf.d/default.conf && \
    echo '    gzip on;' >> /etc/nginx/conf.d/default.conf && \
    echo '    gzip_types text/plain text/css application/json application/javascript text/xml application/xml application/xml+rss text/javascript application/wasm;' >> /etc/nginx/conf.d/default.conf && \
    echo '' >> /etc/nginx/conf.d/default.conf && \
    echo '    # Cache para recursos estáticos' >> /etc/nginx/conf.d/default.conf && \
    echo '    location ~* \.(js|css|png|jpg|jpeg|gif|ico|wasm)$ {' >> /etc/nginx/conf.d/default.conf && \
    echo '        expires 1y;' >> /etc/nginx/conf.d/default.conf && \
    echo '        add_header Cache-Control "public, immutable";' >> /etc/nginx/conf.d/default.conf && \
    echo '    }' >> /etc/nginx/conf.d/default.conf && \
    echo '' >> /etc/nginx/conf.d/default.conf && \
    echo '    # SPA fallback' >> /etc/nginx/conf.d/default.conf && \
    echo '    location / {' >> /etc/nginx/conf.d/default.conf && \
    echo '        try_files $uri $uri/ /index.html;' >> /etc/nginx/conf.d/default.conf && \
    echo '    }' >> /etc/nginx/conf.d/default.conf && \
    echo '}' >> /etc/nginx/conf.d/default.conf

# Expõe a porta 80
EXPOSE 80

# Comando para iniciar o nginx
CMD ["nginx", "-g", "daemon off;"]

