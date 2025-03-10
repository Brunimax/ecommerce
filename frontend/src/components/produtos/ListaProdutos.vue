<template>
    <v-card>
      <v-card-title>
        <span class="headline">Lista de Produtos</span>
        <v-spacer></v-spacer>
        <v-btn color="primary" @click="$emit('criar')">
          <v-icon left>mdi-plus</v-icon>
          Novo Produto
        </v-btn>
      </v-card-title>

      <v-data-table
        :headers="headers"
        :items="produtos"
        :loading="carregando"
        :items-per-page="5"
        class="elevation-1"
      >
        <template v-slot:item.preco="{ item }">
          {{ formatarPreco(item.preco) }}
        </template>

        <template v-slot:item.actions="{ item }">
          <v-icon small class="mr-2" @click="$emit('editar', item)">
            mdi-pencil
          </v-icon>
          <v-icon small color="error" @click="$emit('excluir', item)">
            mdi-delete
          </v-icon>
        </template>

        <template v-slot:progress>
          <v-progress-linear indeterminate color="primary"></v-progress-linear>
        </template>
      </v-data-table>

      <v-alert v-if="erro" type="error" class="ma-4">
        Erro ao carregar produtos: {{ erro }}
      </v-alert>
    </v-card>
</template>

<script>
export default {
  props: {
    produtos: {
      type: Array,
      required: true,
      default: () => []
    },
    carregando: {
      type: Boolean,
      default: false
    },
    erro: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      headers: [
        { text: 'ID', value: 'id', width: '100px' },
        { text: 'Nome', value: 'nome' },
        { text: 'Preço', value: 'preco', align: 'right' },
        { text: 'Ações', value: 'actions', sortable: false, width: '120px', align: 'center' }
      ]
    };
  },
  methods: {
    formatarPreco(preco) {
      try {
        return new Intl.NumberFormat('pt-BR', {
          style: 'currency',
          currency: 'BRL'
        }).format(preco);
      } catch (e) {
        return 'Preço inválido';
      }
    }
  }
};
</script>

<style scoped>
.v-data-table >>> th {
  font-weight: bold;
}
.v-data-table >>> .text-right {
  text-align: right;
}
</style>
