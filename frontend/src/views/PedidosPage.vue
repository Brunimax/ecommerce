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

        <!-- Modal de Visualização -->
        <VisualizacaoPedido
          v-if="mostrarVisualizacao"
          :pedido="pedidoSelecionado"
          @fechar="fecharModalVisualizacao"
        />

        <!-- Modal de Confirmação -->
        <DialogoConfirmacao 
          v-model="mostrarConfirmacaoExclusao"
          titulo="Confirmar Exclusão"
          mensagem="Tem certeza que deseja excluir este pedido?"
          @confirmar="excluirPedido"
        />
      </v-container>
    </BackgroundWrapper>
  </div>
</template>

<script>
import ListaPedidos from '@/components/pedidos/ListaPedidos'
import FormularioPedido from '@/components/pedidos/FormularioPedido'
import DialogoConfirmacao from '@/components/pedidos/DialogoConfirmacao'
import VisualizacaoPedido from '@/components/pedidos/VisualizacaoPedido'
import api from '@/services/api'

export default {
  components: {
    ListaPedidos,
    FormularioPedido,
    DialogoConfirmacao,
    VisualizacaoPedido
  },
  data() {
    return {
      pedidos: [],
      pedidoSelecionado: null,
      mostrarFormulario: false,
      mostrarVisualizacao: false,
      mostrarConfirmacaoExclusao: false,
      carregando: false
    }
  },
  async mounted() {
    await this.carregarPedidos()
  },
  methods: {
    async carregarPedidos() {
      try {
        this.carregando = true
        const response = await api.get('/pedidos?_embed=itens,endereco')
        
        // Normalizar os dados recebidos do backend para camelCase
        this.pedidos = response.data.map(p => ({
          ...p,
          nomeCliente: p.nome_cliente || p.nomeCliente, // Converter snake_case para camelCase
          endereco: p.endereco || {}
        }))
        
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
        itens: []
      }
      this.mostrarFormulario = true
    },

    abrirEdicao(pedido) {
      this.pedidoSelecionado = { 
        ...pedido,
        nomeCliente: pedido.nome_cliente || pedido.nomeCliente // Garantir ambos os casos
      }
      this.mostrarFormulario = true
    },

    abrirExclusao(pedido) {
      this.pedidoSelecionado = pedido
      this.mostrarConfirmacaoExclusao = true
    },

    abrirVisualizacao(pedido) {
      this.pedidoSelecionado = { 
        ...pedido,
        nomeCliente: pedido.nome_cliente || pedido.nomeCliente // Garantir ambos os casos
      }
      this.mostrarVisualizacao = true
    },

    async salvarPedido(pedido) {
      try {
        const payload = {
          ...pedido,
          nome_cliente: pedido.nomeCliente, // Converter para snake_case para o backend
          endereco_id: pedido.endereco?.id || null // Garantir consistência do endereço
        }

        if (pedido.id) {
          await api.put(`/pedidos/${pedido.id}`, payload)
        } else {
          await api.post('/pedidos', payload)
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

    fecharModalVisualizacao() {
      this.mostrarVisualizacao = false
      this.pedidoSelecionado = null
    },

    fecharModalExclusao() {
      this.mostrarConfirmacaoExclusao = false
      this.pedidoSelecionado = null
    }
  }
}
</script>
