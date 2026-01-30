# Quick Setup Guide

## Step 1: Install Dependencies

### Install Node.js dependencies for TailwindCSS:
```bash
npm install
```

If npm install fails, you can use TailwindCSS CDN instead by uncommenting the CDN link in the HTML templates.

## Step 2: Build TailwindCSS

### Windows:
```bash
build-css.bat
```

### Linux/Mac:
```bash
chmod +x build-css.sh
./build-css.sh
```

### Or manually:
```bash
npx tailwindcss -i ./src/main/resources/static/css/input.css -o ./src/main/resources/static/css/output.css --minify
```

## Step 3: Start MongoDB

Make sure MongoDB is running on `localhost:27017`

## Step 4: Run the Application

```bash
mvn spring-boot:run
```

The application will be available at: `http://localhost:8080`

## Alternative: Use TailwindCSS CDN (Quick Start)

If you want to skip the TailwindCSS build step, you can use the CDN version:

1. Open any template file (index.html, proyectos.html, busqueda.html)
2. Uncomment the CDN link:
   ```html
   <link href="https://cdn.jsdelivr.net/npm/tailwindcss@3.4.0/dist/tailwind.min.css" rel="stylesheet">
   ```
3. Comment out the local CSS link:
   ```html
   <!-- <link rel="stylesheet" th:href="@{/css/output.css}"> -->
   ```

This is fine for development but not recommended for production.
