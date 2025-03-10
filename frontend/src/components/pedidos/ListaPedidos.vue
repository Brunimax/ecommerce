<template>
    <v-card>
      <v-card-title>
        Lista de Pedidos
        <v-spacer></v-spacer>
        <v-btn color="primary" @click.native="$emit('criar')">
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
        <!-- Coluna do Cliente -->
        <template v-slot:item.nomeCliente="{ item }">
          {{ item.nomeCliente || 'Não informado' }}
        </template>
  
        <!-- Coluna do CEP -->
        <template v-slot:item.endereco.cep="{ item }">
          {{ item.endereco?.cep || 'N/D' }}
        </template>
  
        <!-- Coluna do Status -->
        <template v-slot:item.status="{ item }">
          <v-chip :color="corStatus(item.status)" dark small>
            {{ item.status }}
          </v-chip>
        </template>
  
        <!-- Coluna de Ações -->
        <template v-slot:item.actions="{ item }">
          <v-icon small class="mr-2" @click.native="$emit('visualizar', item)">
            mdi-eye
          </v-icon>
          <v-icon small class="mr-2" @click.native="$emit('editar', item)">
            mdi-pencil
          </v-icon>
          <v-icon small color="error" @click.native="$emit('excluir', item)">
            mdi-delete
          </v-icon>
        </template>
      </v-data-table>
    </v-card>
  </template>
  
  <script>
  export default {
    props: {
      pedidos: {
        type: Array,
        required: true
      },
      carregando: {
        type: Boolean,
        default: false
      }
    },
    data: () => ({
      headers: [
        { text: 'ID', value: 'id', width: '100' },
        { text: 'Cliente', value: 'nomeCliente' },
        { text: 'CEP', value: 'endereco.cep' },
        { text: 'Status', value: 'status' },
        { text: 'Ações', value: 'actions', sortable: false, width: '150' }
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
  