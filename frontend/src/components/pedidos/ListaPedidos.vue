<template>
    <v-card>
      <v-card-title>
        Lista de Pedidos
        <v-spacer></v-spacer>
        <v-btn color="primary" @click="$emit('criar')">
          <v-icon left>mdi-plus</v-icon>
          Novo Pedido
        </v-btn>
      </v-card-title>
  
      <v-data-table
        :headers="headers"
        :items="pedidos"
        :loading="carregando"
        :items-per-page="10"
      >
        <template v-slot:item.status="{ item }">
          <v-chip :color="corStatus(item.status)" dark small>
            {{ item.status }}
          </v-chip>
        </template>
  
        <template v-slot:item.actions="{ item }">
          <v-icon small class="mr-2" @click.stop="$emit('visualizar', item)">
            mdi-eye
          </v-icon>
          <v-icon small @click.stop="$emit('mudarStatus', item)">
            mdi-pencil
          </v-icon>
        </template>
      </v-data-table>
    </v-card>
  </template>
  
  <script>
  export default {
    props: ['pedidos', 'carregando'],
    data: () => ({
      headers: [
        { text: 'ID', value: 'id' },
        { text: 'Cliente', value: 'nomeCliente' },
        { text: 'Status', value: 'status' },
        { text: 'Ações', value: 'actions', sortable: false }
      ]
    }),
    methods: {
      corStatus(status) {
        const cores = {
          CRIADO: 'blue',
          PROCESSANDO: 'orange',
          ENVIADO: 'green',
          ENTREGUE: 'teal',
          CANCELADO: 'red'
        }
        return cores[status] || 'grey'
      }
    }
  }
  </script>
  