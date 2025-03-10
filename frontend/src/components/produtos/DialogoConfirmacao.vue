<template>
    <v-dialog v-model="dialogo" max-width="400px">
      <v-card>
        <v-card-title class="headline">{{ titulo }}</v-card-title>
        <v-card-text>{{ mensagem }}</v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn text @click="$emit('cancelar')">Cancelar</v-btn>
          <v-btn color="error" @click="$emit('confirmar')">Confirmar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </template>
  
  <script>
  export default {
    props: {
      titulo: String, // Título do modal
      mensagem: String, // Mensagem de confirmação
      valor: Boolean // Controla a abertura do modal
    },
    computed: {
      dialogo: {
        get() {
          return this.valor;
        },
        set(valor) {
          this.$emit('input', valor); // Atualiza o estado do modal no componente pai
        }
      }
    }
  };
  </script>
  
  <style scoped>
  /* Estilos opcionais */
  </style>
  