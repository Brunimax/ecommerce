<template>
    <v-dialog v-model="dialog" max-width="800px">
      <v-card>
        <v-card-title>
          Detalhes do Pedido #{{ pedido.id }}
        </v-card-title>
  
        <v-card-text>
          <v-list>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>Cliente: {{ pedido.nome_cliente }}</v-list-item-title>
                <v-list-item-subtitle>Status: {{ pedido.status }}</v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
  
            <v-divider></v-divider>
  
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>Endereço</v-list-item-title>
                <v-list-item-subtitle>
                  {{ enderecoCompleto }}
                </v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
  
            <v-divider></v-divider>
  
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>Itens do Pedido</v-list-item-title>
                <v-list-item-subtitle>
                  <div v-for="(item, index) in pedido.itens" :key="index">
                    {{ item.quantidade }}x {{ item.produto.nome }} 
                    ({{ formatarPreco(item.preco_unitario) }})
                  </div>
                </v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
          </v-list>
        </v-card-text>
      </v-card>
    </v-dialog>
  </template>
  
  <script>
  export default {
    props: ['pedido'],
    computed: {
      dialog: {
        get() { return !!this.pedido },
        set() { this.$emit('fechar') }
      },
      enderecoCompleto() {
        const e = this.pedido.endereco
        return `${e.rua}, ${e.bairro}, ${e.cidade}/${e.uf} - CEP: ${e.cep}`
      }
    },
    methods: {
      formatarPreco(preco) {
        return new Intl.NumberFormat('pt-BR', {
          style: 'currency',
          currency: 'BRL'
        }).format(preco)
      }
    }
  }
  </script>
  