# Stage 1: Build the Angular application
FROM node:20-alpine3.16

WORKDIR /usr/local/app

# Add the source code to app
COPY . .

RUN npm install -g npm@9.6.7

RUN npm config set legacy-peer-deps true

# Install all the dependencies
RUN npm install

# Generate the build of the application
RUN npm run build --prod

# this is just documentation for the person reading this file and has no real function
EXPOSE 4200

CMD ["npm", "start"]

