# Angular Frontend – Searchengine

This project is a frontend Angular application built with a modular component structure and scalable SCSS architecture.

It was generated using [Angular CLI](https://github.com/angular/angular-cli) version 19.2.8.

---

## 📦 Prerequisites

Make sure these are installed before working with the frontend:

| Tool          | Version | Install Command / Link                               |
|---------------|---------|--------------------------------------------------------|
| Node.js + npm | 18+     | [Node.js LTS](https://nodejs.org/)                    |
| Angular CLI   | Latest  | `npm install -g @angular/cli`                         |

---

## 🚀 Development Server

Start a local Angular dev server with:

```bash
npm install
ng serve
```

Visit the app at:
```
http://localhost:4200/
```

---

## 📁 Project Structure

```
frontend/
├── src/
│   ├── app/
│   │   ├── app.component.*
│   │   ├── sign-in/
│   │   ├── user-create/
│   │   └── search/
│   │       ├── search-input/
│   │       ├── search-results/
│   │       └── search-history/
│   └── styles/
│       ├── _variables.scss
│       ├── _globals.scss
│       ├── _mixins.scss
│       └── main.scss
```

- Each component has its own `.html`, `.scss`, and `.ts`
- Global SCSS is organized into reusable variables and mixins
- Uses standalone components for modular design

---

## ⚙️ Code Scaffolding

Generate a new standalone component:
```bash
ng generate component component-name --standalone --style=scss
```

See all options:
```bash
ng generate --help
```

---

## 🛠️ Building

To compile the application locally:
```bash
ng build
```
Output goes to:
```
dist/searchengine/browser/
```
> This folder is used in Docker builds to serve content with Nginx

---

## ✅ Running Unit Tests

```bash
ng test
```
Uses Karma test runner and Angular TestBed

---

## 🔍 End-to-End Tests

```bash
ng e2e
```
You can install your own e2e framework (like Cypress or Playwright) if needed.

---

## 💡 Developer Tips
- Define shared SCSS styles in `src/styles/`
- Use `--standalone` when generating new components
- Stick to one UI role per component for reusability
- Add imports manually for standalone components
- Keep styling modular with one `.scss` file per component

---

## 📚 Resources
- [Angular CLI Docs](https://angular.dev/tools/cli)
- [Angular Dev Guides](https://angular.dev/)
- [SCSS Reference](https://sass-lang.com/guide)

Happy coding! 🎉
