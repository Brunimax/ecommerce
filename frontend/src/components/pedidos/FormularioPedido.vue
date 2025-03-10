<template>
    <v-dialog v-model="dialog" max-width="800px" persistent>
      <v-card>
        <v-card-title>
          {{ pedidoLocal.id ? 'Editar Pedido' : 'Novo Pedido' }}
        </v-card-title>
  
        <v-card-text>
          <v-form ref="form">
            <!-- Campo corrigido para camelCase -->
            <v-text-field
              v-model="pedidoLocal.nomeCliente"
              label="Nome do Cliente"
              :rules="[v => !!v || 'Campo obrigatório']"
            ></v-text-field>
  
            <!-- Busca de Endereço por CEP -->
            <v-text-field
              v-model="pedidoLocal.endereco.cep"
              label="CEP"
              @blur="buscarEndereco"
              :rules="[v => /^[0-9]{8}$/.test(v) || 'CEP inválido']"
            ></v-text-field>
  
            <!-- Campos de Endereço -->
            <v-row>
              <v-col cols="8">
                <v-text-field
                  v-model="pedidoLocal.endereco.rua"
                  label="Rua"
                ></v-text-field>
              </v-col>
              <v-col cols="4">
                <v-text-field
                  v-model="pedidoLocal.endereco.bairro"
                  label="Bairro"
                ></v-text-field>
              </v-col>
            </v-row>
  
            <!-- Seleção de Produtos -->
            <v-autocomplete
              v-model="produtoSelecionado"
              :items="produtos"
              item-text="nome"
              item-value="id"
              label="Selecione um Produto"
              :loading="carregandoProdutos"
            ></v-autocomplete>
  
            <!-- Quantidade e Adição -->
            <v-row>
              <v-col cols="3">
                <v-text-field
                  v-model.number="quantidade"
                  type="number"
                  label="Quantidade"
                  min="1"
                ></v-text-field>
              </v-col>
              <v-col cols="9">
                <v-btn 
                  color="primary" 
                  @click="adicionarItem"
                  :disabled="!produtoSelecionado || !quantidade"
                >
                  Adicionar ao Pedido
                </v-btn>
              </v-col>
            </v-row>
  
            <!-- Itens do Pedido -->
            <v-list>
              <v-list-item 
                v-for="(item, index) in pedidoLocal.itens" 
                :key="index"
              >
                <v-list-item-content>
                  {{ item.produto.nome }} - 
                  {{ item.quantidade }} x 
                  {{ formatarPreco(item.preco_unitario) }}
                </v-list-item-content>
                <v-list-item-action>
                  <v-btn icon @click="removerItem(index)">
                    <v-icon color="error">mdi-delete</v-icon>
                  </v-btn>
                </v-list-item-action>
              </v-list-item>
            </v-list>
          </v-form>
        </v-card-text>
  
        <!-- Botões de Ação (Corrigir posicionamento) -->
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn text @click="$emit('fechar')">Cancelar</v-btn>
          <v-btn color="primary" @click="salvar">Salvar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </template>
  
  <script>
  import api from '@/services/api'
  
  export default {
    props: ['pedido'],
    data: () => ({
      pedidoLocal: null,
      produtos: [],
      produtoSelecionado: null,
      quantidade: 1,
      carregandoProdutos: false
    }),
    computed: {
      dialog: {
        get() { return !!this.pedido },
        set() { this.$emit('fechar') }
      }
    },
    watch: {
      pedido: {
        immediate: true,
        handler(newVal) {
          this.pedidoLocal = {
            ...newVal,
            nomeCliente: newVal?.nome_cliente || newVal?.nomeCliente || '',
            endereco: newVal?.endereco || {}
          }
        }
      }
    },
    methods: {
      async carregarProdutos() {
        try {
          this.carregandoProdutos = true
          const response = await api.get('/produtos')
          this.produtos = response.data
        } catch (error) {
          console.error('Erro ao carregar produtos:', error)
        } finally {
          this.carregandoProdutos = false
        }
      },
  
      async buscarEndereco() {
        if (this.pedidoLocal.endereco.cep?.length === 8) {
          try {
            const response = await api.get(`/enderecos/${this.pedidoLocal.endereco.cep}`)
            this.pedidoLocal.endereco = response.data
          } catch (error) {
            console.error('CEP não encontrado:', error)
          }
        }
      },
  
      adicionarItem() {
        const produto = this.produtos.find(p => p.id === this.produtoSelecionado)
        this.pedidoLocal.itens.push({
          produto_id: produto.id,
          produto,
          quantidade: this.quantidade,
          preco_unitario: produto.preco
        })
        this.produtoSelecionado = null
        this.quantidade = 1
      },
  
      removerItem(index) {
        this.pedidoLocal.itens.splice(index, 1)
      },
  
      salvar() {
        this.$emit('salvar', JSON.parse(JSON.stringify(this.pedidoLocal)))
      },
  
      formatarPreco(preco) {
        return new Intl.NumberFormat('pt-BR', {
          style: 'currency',
          currency: 'BRL'
        }).format(preco)
      }
    }
  }
  </script>
  