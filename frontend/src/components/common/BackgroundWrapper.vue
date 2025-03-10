<template>
    <div class="background-wrapper">
      <v-container fill-height>
        <slot></slot> <!-- Conteúdo das páginas -->
      </v-container>
    </div>
  </template>
  
  <script>
  export default {
    name: 'BackgroundWrapper'
  }
  </script>
  
  <style scoped>
  .background-wrapper {
    background-color: white;
    min-height: 100vh;
  }
  </style>
  