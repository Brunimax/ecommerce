<template>
    <v-dialog v-model="dialog" max-width="800px" persistent>
      <v-card v-if="pedido">
        <v-card-title>
          Detalhes do Pedido #{{ pedido.pedido.id }}
        </v-card-title>
  
        <v-card-text>
          <v-list>
            <!-- Informações do Cliente -->
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>Cliente: {{ pedido.pedido.nomeCliente }}</v-list-item-title>
                <v-list-item-subtitle>Status: {{ pedido.pedido.status }}</v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
  
            <v-divider></v-divider>
  
            <!-- Endereço -->
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>Endereço</v-list-item-title>
                <v-list-item-subtitle>
                  {{ enderecoCompleto }}
                </v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
  
            <v-divider></v-divider>
  
            <!-- Itens do Pedido -->
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>Itens do Pedido</v-list-item-title>
                <v-list-item-subtitle>
                  <div 
                    v-for="(produtoDTO, index) in pedido.produtos" 
                    :key="index"
                    class="my-2"
                  >
                    {{ produtoDTO.quantidade }}x {{ produtoDTO.produto.nome }} 
                    ({{ formatarPreco(produtoDTO.produto.preco) }})
                  </div>
                </v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
          </v-list>
        </v-card-text>
  
        <!-- Botão Fechar -->
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" text @click="$emit('fechar')">Fechar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </template>
  
  <script>
  export default {
    props: ['pedido'],
    computed: {
      dialog: {
        get() { return !!this.pedido },
        set(value) { if (!value) this.$emit('fechar') }
      },
      enderecoCompleto() {
        const e = this.pedido?.pedido?.endereco || {}
        return `${e.rua || ''}, ${e.bairro || ''}, ${e.cidade || ''}/${e.uf || ''} - CEP: ${e.cep || ''}`
      }
    },
    methods: {
      formatarPreco(preco) {
        return new Intl.NumberFormat('pt-BR', { 
          style: 'currency', 
          currency: 'BRL' 
        }).format(preco || 0)
      }
    }
  }
  </script>
  