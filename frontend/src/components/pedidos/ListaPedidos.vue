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

      <template v-slot:item.produtos="{ item }">
        <div v-for="(itemPedido, index) in item.itens" :key="index">
          {{ itemPedido.quantidade }}x {{ itemPedido.produto.nome }}
        </div>
      </template>

      <template v-slot:item.actions="{ item }">
        <v-icon small @click="$emit('visualizar', item)" class="mr-2">
          mdi-eye
        </v-icon>
        <v-icon small @click="$emit('editar', item)" class="mr-2">
          mdi-pencil
        </v-icon>
        <v-icon small color="error" @click="$emit('excluir', item)">
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
      { text: 'Cliente', value: 'nome_cliente' },
      { text: 'Produtos', value: 'produtos' },
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
