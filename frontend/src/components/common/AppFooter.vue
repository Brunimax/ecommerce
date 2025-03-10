<template>
    <v-footer color="primary" dark padless>
      <v-container>
        <div class="text-center">
          &copy; {{ new Date().getFullYear() }} E-Commerce
        </div>
      </v-container>
    </v-footer>
  </template>
  
  <script>
  export default {
    name: 'AppFooter'
  }
  </script>
  