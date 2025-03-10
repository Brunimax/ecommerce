<template>
    <v-container class="text-center">
      <v-row justify="center" align="center" class="my-10">
        <v-col cols="12" md="8">
          <!-- Título de Boas-Vindas -->
          <h1 class="display-2 font-weight-bold primary--text">
            Bem-vindo ao Sistema de Cadastro de Pedidos de Produtos!
          </h1>
  
          <!-- Descrição do Projeto -->
          <p class="subtitle-1 mt-4">
            Este sistema foi desenvolvido para facilitar o cadastro de pedidos de produtos,
            permitindo que você gerencie pedidos de forma eficiente e organizada.
          </p>
  
          <!-- Objetivo do Projeto -->
          <v-card outlined class="mt-6">
            <v-card-text>
              <h2 class="headline font-weight-bold">Objetivo do Projeto</h2>
              <p>
                O objetivo principal é oferecer uma solução simples e intuitiva para gerenciar
                pedidos de produtos, permitindo que você:
              </p>
              <ul class="text-left">
                <li>Cadastre novos pedidos com facilidade.</li>
                <li>Gerencie produtos associados a cada pedido.</li>
                <li>Visualize e edite informações de pedidos existentes.</li>
                <li>Exclua pedidos que não são mais necessários.</li>
              </ul>
            </v-card-text>
          </v-card>
  
          <!-- Botão para Navegar -->
          <v-btn color="primary" large class="mt-6" @click="$router.push('/produtos')">
            Gerenciar Produtos
            <v-icon right>mdi-arrow-right</v-icon>
          </v-btn>
        </v-col>
      </v-row>
    </v-container>
  </template>
  
  <script>
  export default {
    name: "Home"
  };
  </script>
  
  <style scoped>
  /* Estilos personalizados para a tela inicial */
  .primary--text {
    color: #1976D2 !important; /* Azul primário */
  }
  
  ul {
    list-style-type: disc;
    margin-left: 20px;
  }
  </style>
  