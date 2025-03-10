<!-- src/components/common/AppHeader.vue -->
<template>
    <v-app-bar app color="primary" dark>
      <!-- Logo -->
      <router-link to="/">
        <v-toolbar-title class="white--text">
          <v-icon large>mdi-cart</v-icon>
          E-Commerce
        </v-toolbar-title>
      </router-link>
  
      <v-spacer></v-spacer>
  
      <!-- Links de Navegação -->
      <v-toolbar-items class="hidden-sm-and-down">
        <v-btn text to="/">
          <v-icon left>mdi-home</v-icon>
          Home
        </v-btn>
        <v-btn text to="/pedidos">
          <v-icon left>mdi-format-list-bulleted</v-icon>
          Pedidos
        </v-btn>
        <v-btn text to="/produtos">
          <v-icon left>mdi-package-variant</v-icon>
          Produtos
        </v-btn>
      </v-toolbar-items>
  
      <!-- Menu de Usuário (Mobile) -->
      <v-menu bottom left class="hidden-md-and-up">
        <template v-slot:activator="{ on }">
          <v-btn icon v-on="on">
            <v-icon>mdi-dots-vertical</v-icon>
          </v-btn>
        </template>
        <v-list>
          <v-list-item to="/">
            <v-list-item-title>Home</v-list-item-title>
          </v-list-item>
          <v-list-item to="/pedidos">
            <v-list-item-title>Pedidos</v-list-item-title>
          </v-list-item>
          <v-list-item to="/produtos">
            <v-list-item-title>Produtos</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-app-bar>
  </template>
  
  <script>
  export default {
    name: 'AppHeader'
  }
  </script>
  
  <style scoped>
  a {
    text-decoration: none;
  }
  </style>
  