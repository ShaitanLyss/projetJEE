/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
      "./src/main/resources/templates/**/*.{html,js}",
    './node_modules/tw-elements/dist/js/**/*.js'
      // "./src/main/assets/styles"
  ],
  theme: {
    extend: {},
  },
  plugins: [
    require('tw-elements/dist/plugin')
  ],
}
