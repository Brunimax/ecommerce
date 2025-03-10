<template>
    <v-dialog v-model="dialog" max-width="600px">
      <v-card>
        <v-card-title>{{ formTitle }}</v-card-title>
        <v-card-text>
          <!-- Campos do formulário -->
          <v-form ref="form">
            <v-text-field
              v-model="formData.nome"
              label="Nome"
              :rules="[v => !!v || 'Nome é obrigatório']"
              required
            ></v-text-field>
            <v-text-field
              v-model="formData.preco"
              label="Preço"
              type="number"
              prefix="R$"
              :rules="[v => v >= 0 || 'Preço deve ser maior ou igual a 0']"
            ></v-text-field>
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
  export default {
    props: {
      produto: Object // Produto selecionado ou null para novo produto
    },
    data() {
      return {
        formData: {
          id: null,
          nome: '',
          preco: 0
        }
      };
    },
    computed: {
      dialog: {
        get() {
          return this.produto !== null; // Abre o modal se houver produto selecionado
        },
        set() {
          this.$emit('fechar'); // Fecha o modal
        }
      },
      formTitle() {
        return this.formData.id ? 'Editar Produto' : 'Novo Produto';
      }
    },
    watch: {
      produto: {
        immediate: true,
        handler(newVal) {
          if (newVal) {
            this.formData = { ...newVal }; // Preenche os campos para edição
          } else {
            this.resetForm(); // Reseta o formulário para novo produto
          }
        }
      }
    },
    methods: {
      resetForm() {
        this.formData = { id: null, nome: '', preco: 0 };
      },
      salvar() {
        if (this.$refs.form.validate()) {
          this.$emit('salvar', this.formData); // Emite o evento com os dados do formulário
        }
      }
    }
  };
  </script>
  
  <style scoped>
  /* Estilos opcionais */
  </style>
  