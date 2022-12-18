/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
      "./src/main/resources/templates/**/*.{html,js}",
    './node_modules/tw-elements/dist/js/**/*.js'
      // "./src/main/assets/styles"
  ],
  prefix: 'tw-',
  theme: {
    extend: {},
  },
  important: true,
  plugins: [
    require('tw-elements/dist/plugin')
  ],
}
