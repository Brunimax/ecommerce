<template>
  <div>
    <AppHeader />
    <BackgroundWrapper>
      <v-container>
        <ListaPedidos 
          :pedidos="pedidos"
          :carregando="carregando"
          @criar="abrirNovoPedido"
          @editar="abrirEdicao"
          @excluir="abrirExclusao"
          @visualizar="abrirVisualizacao"
        />
        
        <!-- Modal de Edição/Criação -->
        <FormularioPedido 
          v-if="mostrarFormulario"
          :pedido="pedidoSelecionado"
          @fechar="fecharModalFormulario"
          @salvar="salvarPedido"
        />
        
        <!-- Modal de Confirmação -->
        <DialogoConfirmacao 
          v-if="mostrarConfirmacaoExclusao"
          titulo="Confirmar Exclusão"
          mensagem="Tem certeza que deseja excluir este pedido?"
          @confirmar="excluirPedido"
          @cancelar="fecharModalExclusao"
        />
      </v-container>
    </BackgroundWrapper>
  </div>
</template>

<script>
import ListaPedidos from '@/components/pedidos/ListaPedidos'
import FormularioPedido from '@/components/pedidos/FormularioPedido'
import DialogoConfirmacao from '@/components/pedidos/DialogoConfirmacao'
import api from '@/services/api'

export default {
  components: {
    ListaPedidos,
    FormularioPedido,
    DialogoConfirmacao
  },
  data: () => ({
    pedidos: [],
    pedidoSelecionado: null,
    mostrarFormulario: false,
    mostrarConfirmacaoExclusao: false,
    carregando: false
  }),
  async mounted() {
    await this.carregarPedidos()
  },
  methods: {
    async carregarPedidos() {
      try {
        this.carregando = true
        const response = await api.get('/pedidos?_embed=itens')
        this.pedidos = response.data
      } catch (error) {
        console.error('Erro ao carregar pedidos:', error)
      } finally {
        this.carregando = false
      }
    },

    abrirNovoPedido() {
      this.pedidoSelecionado = { nome_cliente: '', status: 'CRIADO', endereco: {}, itens: [] }
      this.mostrarFormulario = true
    },

    abrirEdicao(pedido) {
      this.pedidoSelecionado = { ...pedido }
      this.mostrarFormulario = true
    },

    abrirExclusao(pedido) {
      this.pedidoSelecionado = pedido
      this.mostrarConfirmacaoExclusao = true
    },

    async salvarPedido(pedido) {
      try {
        if (pedido.id) {
          await api.put(`/pedidos/${pedido.id}`, pedido)
        } else {
          await api.post('/pedidos', pedido)
        }
        await this.carregarPedidos()
        this.fecharModalFormulario()
      } catch (error) {
        console.error('Erro ao salvar pedido:', error)
      }
    },

    async excluirPedido() {
      try {
        await api.delete(`/pedidos/${this.pedidoSelecionado.id}`)
        await this.carregarPedidos()
        this.fecharModalExclusao()
      } catch (error) {
        console.error('Erro ao excluir pedido:', error)
      }
    },

    fecharModalFormulario() {
      this.mostrarFormulario = false
      this.pedidoSelecionado = null
    },

    fecharModalExclusao() {
      this.mostrarConfirmacaoExclusao = false
      this.pedidoSelecionado = null
    }
  }
}
</script>
