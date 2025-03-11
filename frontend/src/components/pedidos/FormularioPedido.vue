<template>
    <v-dialog v-model="dialog" max-width="800px" persistent>
      <v-card>
        <v-card-title>
          {{ pedidoLocal.id ? 'Editar Pedido' : 'Novo Pedido' }}
        </v-card-title>
  
        <v-card-text>
          <v-form ref="form">
            <v-text-field
              v-model="pedidoLocal.nomeCliente"
              label="Nome do Cliente"
              :rules="[v => !!v || 'Campo obrigatório']"
            ></v-text-field>
  
            <v-text-field
              v-model="cepFormatado"
              label="CEP"
              placeholder="00000-000"
              v-mask="'#####-###'"
              @blur="buscarEndereco"
              :rules="[
                v => !!v || 'CEP obrigatório',
                v => /^[0-9]{5}-[0-9]{3}$/.test(v) || 'CEP inválido'
              ]"
            ></v-text-field>
  
            <v-row>
              <v-col cols="8">
                <v-text-field
                  v-model="pedidoLocal.endereco.rua"
                  label="Rua"
                  :rules="[v => !!v || 'Campo obrigatório']"
                ></v-text-field>
              </v-col>
              <v-col cols="4">
                <v-text-field
                  v-model="pedidoLocal.endereco.bairro"
                  label="Bairro"
                  :rules="[v => !!v || 'Campo obrigatório']"
                ></v-text-field>
              </v-col>
            </v-row>
  
            <v-row>
              <v-col cols="8">
                <v-text-field
                  v-model="pedidoLocal.endereco.cidade"
                  label="Cidade"
                  :rules="[v => !!v || 'Campo obrigatório']"
                ></v-text-field>
              </v-col>
              <v-col cols="4">
                <v-text-field
                  v-model="pedidoLocal.endereco.uf"
                  label="UF"
                  :rules="[
                    v => !!v || 'Campo obrigatório',
                    v => (v && v.length === 2) || 'UF deve ter 2 caracteres'
                  ]"
                ></v-text-field>
              </v-col>
            </v-row>
  
            <v-autocomplete
              v-model="produtoSelecionado"
              :items="produtos"
              item-text="nome"
              item-value="id"
              label="Selecione um Produto"
              :loading="carregandoProdutos"
            ></v-autocomplete>
  
            <v-row>
              <v-col cols="3">
                <v-text-field
                  v-model.number="quantidade"
                  type="number"
                  label="Quantidade"
                  min="1"
                  :rules="[v => v > 0 || 'Quantidade mínima: 1']"
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
  
            <v-list v-if="pedidoLocal.produtos.length">
              <v-list-item 
                v-for="(item, index) in pedidoLocal.produtos" 
                :key="index"
              >
                <v-list-item-content>
                  {{ item.produto.nome }} - 
                  {{ item.quantidade }} x 
                  {{ formatarPreco(item.produto.preco) }}
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
  import { mask } from 'vue-the-mask'
  
  export default {
    directives: { mask },
    props: ['pedido'],
    data: () => ({
      pedidoLocal: {
        nomeCliente: '',
        status: 'CRIADO',
        endereco: {},
        produtos: []
      },
      produtos: [],
      produtoSelecionado: null,
      quantidade: 1,
      carregandoProdutos: false
    }),
    computed: {
      dialog: {
        get() { return !!this.pedido },
        set() { this.$emit('fechar') }
      },
      cepFormatado: {
        get() {
          return this.pedidoLocal.endereco.cep?.replace(/(\d{5})(\d{3})/, '$1-$2') || ''
        },
        set(value) {
          this.pedidoLocal.endereco.cep = value.replace(/\D/g, '')
        }
      }
    },
    watch: {
      pedido: {
        immediate: true,
        deep: true,
        handler(newVal) {
          this.pedidoLocal = newVal ? { 
            ...newVal,
            endereco: newVal.endereco || {},
            produtos: newVal.produtos || []
          } : {
            nomeCliente: '',
            status: 'CRIADO',
            endereco: {},
            produtos: []
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
        const cep = this.pedidoLocal.endereco.cep.replace(/\D/g, '')
        if (cep.length !== 8) return
        
        try {
          const response = await fetch(`https://viacep.com.br/ws/${cep}/json/`)
          const data = await response.json()
          
          if (data.erro) throw new Error('CEP não encontrado')
          
          this.pedidoLocal.endereco = {
            cep: cep,
            rua: data.logradouro || '',
            bairro: data.bairro || '',
            cidade: data.localidade || '',
            uf: data.uf || ''
          }
        } catch (error) {
          this.$store.dispatch('showSnackbar', {
            message: 'CEP inválido ou não encontrado',
            color: 'error'
          })
        }
      },
  
      adicionarItem() {
        const produto = this.produtos.find(p => p.id === this.produtoSelecionado)
        this.pedidoLocal.produtos.push({
          produto: produto,
          quantidade: this.quantidade
        })
        this.produtoSelecionado = null
        this.quantidade = 1
      },
  
      removerItem(index) {
        this.pedidoLocal.produtos.splice(index, 1)
      },
  
      salvar() {
        if (this.$refs.form.validate()) {
          this.$emit('salvar', this.pedidoLocal)
        }
      },
  
      formatarPreco(preco) {
        return new Intl.NumberFormat('pt-BR', { 
          style: 'currency', 
          currency: 'BRL' 
        }).format(preco || 0)
      }
    },
    mounted() {
      this.carregarProdutos()
    }
  }
  </script>
  