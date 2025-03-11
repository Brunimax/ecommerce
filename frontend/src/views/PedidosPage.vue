<template>
  <div>
    <AppHeader />
    <BackgroundWrapper>
      <v-container>
        <!-- Lista de Pedidos -->
        <ListaPedidos 
          :pedidos="pedidos"
          :carregando="carregando"
          @criar="abrirNovoPedido"
          @visualizar="abrirVisualizacao"
          @mudarStatus="abrirMudarStatus"
        />

        <!-- Modal de Criação/Edição -->
        <FormularioPedido 
          v-if="mostrarFormulario"
          :pedido="pedidoSelecionado"
          @fechar="fecharModalFormulario"
          @salvar="salvarPedido"
        />

        <!-- Modal de Visualização -->
        <VisualizacaoPedido
          v-if="mostrarVisualizacao"
          :pedido="pedidoSelecionado"
          @fechar="fecharModalVisualizacao"
        />

        <!-- Modal de Mudança de Status -->
        <MudarStatusPedido
          v-if="mostrarMudarStatus"
          :pedido="pedidoSelecionado"
          @fechar="fecharModalMudarStatus"
          @salvar="salvarStatus"
        />
      </v-container>
    </BackgroundWrapper>
  </div>
</template>

<script>
import ListaPedidos from '@/components/pedidos/ListaPedidos'
import FormularioPedido from '@/components/pedidos/FormularioPedido'
import VisualizacaoPedido from '@/components/pedidos/VisualizacaoPedido'
import MudarStatusPedido from '@/components/pedidos/MudarStatusPedido'
import api from '@/services/api'

export default {
  components: {
    ListaPedidos,
    FormularioPedido,
    VisualizacaoPedido,
    MudarStatusPedido
  },
  data: () => ({
    pedidos: [],
    pedidoSelecionado: null,
    mostrarFormulario: false,
    mostrarVisualizacao: false,
    mostrarMudarStatus: false,
    carregando: false
  }),
  async mounted() {
    await this.carregarPedidos()
  },
  methods: {
    async carregarPedidos() {
      try {
        this.carregando = true
        const response = await api.get('/pedidos')
        this.pedidos = response.data
      } catch (error) {
        console.error('Erro ao carregar pedidos:', error)
      } finally {
        this.carregando = false
      }
    },

    abrirNovoPedido() {
      this.pedidoSelecionado = {
        nomeCliente: '',
        status: 'CRIADO',
        endereco: {},
        produtos: []
      }
      this.mostrarFormulario = true
    },

    async abrirVisualizacao(pedido) {
      try {
        this.carregando = true
        const response = await api.get(`/pedidos/pedidosProdutos/${pedido.id}`)
        
        // Ajuste para mapear corretamente o DTO recebido
        this.pedidoSelecionado = response.data
        
        this.mostrarVisualizacao = true
      } catch (error) {
        console.error('Erro ao carregar detalhes:', error)
      } finally {
        this.carregando = false
      }
    },

    abrirMudarStatus(pedido) {
      this.pedidoSelecionado = pedido
      this.mostrarMudarStatus = true
    },

    async salvarPedido(pedido) {
      try {
        const url = pedido.id ? `/pedidos/${pedido.id}` : '/pedidos'
        const method = pedido.id ? 'put' : 'post'
        
        await api[method](url, pedido)
        await this.carregarPedidos()
        this.fecharModalFormulario()
      } catch (error) {
        console.error('Erro ao salvar pedido:', error)
      }
    },

    async salvarStatus(pedidoAtualizado) {
      try {
        await api.patch(`/pedidos/${pedidoAtualizado.id}/status`, null, {
          params: { status: pedidoAtualizado.status }
        });
        
        await this.carregarPedidos();
        this.fecharModalMudarStatus();
      } catch (error) {
        console.error('Erro ao atualizar status:', error);
      }
    },

    fecharModalFormulario() {
      this.mostrarFormulario = false
      this.pedidoSelecionado = null
    },

    fecharModalVisualizacao() {
      this.mostrarVisualizacao = false
      this.pedidoSelecionado = null
    },

    fecharModalMudarStatus() {
      this.mostrarMudarStatus = false
      this.pedidoSelecionado = null
    }
  }
}
</script>
