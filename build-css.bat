@echo off
echo Building TailwindCSS...
npx tailwindcss -i ./src/main/resources/static/css/input.css -o ./src/main/resources/static/css/output.css --minify
echo TailwindCSS build complete!
