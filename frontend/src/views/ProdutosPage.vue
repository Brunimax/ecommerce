<template>
  <div>
    <AppHeader />
    <BackgroundWrapper>
      <v-container>
        <ListaProdutos 
          :produtos="produtos" 
          @criar="abrirModalCriacao"
          @editar="abrirModalEdicao"
          @excluir="abrirModalExclusao"
        />
        
        <!-- Modal de Edição/Criação -->
        <FormularioProduto 
          v-if="mostrarFormulario"
          :produto="produtoSelecionado || {}"
          @fechar="fecharModalFormulario"
          @salvar="salvarProduto"
        />
        
        <!-- Modal de Confirmação -->
        <DialogoConfirmacao 
          :valor="mostrarConfirmacaoExclusao"
          titulo="Confirmar Exclusão"
          mensagem="Tem certeza que deseja excluir este produto?"
          @input="mostrarConfirmacaoExclusao = $event"
          @confirmar="excluirProduto"
          @cancelar="fecharModalExclusao"
        />

      </v-container>
    </BackgroundWrapper>
  </div>
</template>

<script>
import ListaProdutos from '@/components/produtos/ListaProdutos'
import FormularioProduto from '@/components/produtos/FormularioProduto'
import DialogoConfirmacao from '@/components/produtos/DialogoConfirmacao'
import api from '@/services/api'

export default {
  components: {
    ListaProdutos,
    FormularioProduto,
    DialogoConfirmacao
  },
  data: () => ({
    produtos: [],
    produtoSelecionado: null,
    mostrarFormulario: false,
    mostrarConfirmacaoExclusao: false
  }),
  async mounted() {
    await this.carregarProdutos();
  },
  methods: {
    async carregarProdutos() {
      try {
        const response = await api.get('/produtos');
        this.produtos = response.data.map(produto => ({
          id: produto.id,
          nome: produto.nome,
          preco: produto.preco
        }));
      } catch (error) {
        console.error('Erro ao carregar produtos:', error);
        this.produtos = [];
      }
    },

    abrirModalCriacao() {
      this.produtoSelecionado = {}; // Alterado de null para um objeto vazio
      this.mostrarFormulario = true;
    },

    abrirModalEdicao(produto) {
      this.produtoSelecionado = { ...produto };
      this.mostrarFormulario = true;
    },

    abrirModalExclusao(produto) {
      console.log('Abrindo modal de exclusão para:', produto);
      if (!produto || !produto.id) {
        console.error("Erro: Nenhum produto válido foi passado para exclusão.");
        return;
      }
      this.produtoSelecionado = produto;
      this.mostrarConfirmacaoExclusao = true;
    },

    fecharModalFormulario() {
      this.mostrarFormulario = false;
      this.produtoSelecionado = null;
    },

    fecharModalExclusao() {
      this.mostrarConfirmacaoExclusao = false;
      this.produtoSelecionado = null;
    },

    async salvarProduto(produtoData) {
      try {
        if (produtoData.id) {
          await api.put(`/produtos/${produtoData.id}`, produtoData);
        } else {
          await api.post('/produtos', produtoData);
        }
        await this.carregarProdutos();
        this.fecharModalFormulario();
      } catch (error) {
        console.error('Erro ao salvar produto:', error);
      }
    },

    async excluirProduto() {
      try {
        if (!this.produtoSelecionado || !this.produtoSelecionado.id) {
          console.error("Erro: Nenhum produto válido para exclusão.");
          return;
        }
        await api.delete(`/produtos/${this.produtoSelecionado.id}`);
        await this.carregarProdutos();
        this.fecharModalExclusao();
      } catch (error) {
        console.error('Erro ao excluir produto:', error);
      }
    }
  }
}
</script>
