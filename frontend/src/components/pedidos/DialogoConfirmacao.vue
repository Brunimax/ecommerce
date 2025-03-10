<template>
    <v-dialog v-model="dialogo" max-width="400px" persistent>
      <v-card>
        <v-card-title class="headline">{{ titulo }}</v-card-title>
        <v-card-text>
          {{ mensagem }}
        </v-card-text>
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
      titulo: {
        type: String,
        default: 'Confirmar Ação'
      },
      mensagem: {
        type: String,
        required: true
      },
      valor: {
        type: Boolean,
        required: true
      }
    },
    computed: {
      dialogo: {
        get() {
          return this.valor
        },
        set(valor) {
          this.$emit('input', valor)
        }
      }
    }
  }
  </script>
  