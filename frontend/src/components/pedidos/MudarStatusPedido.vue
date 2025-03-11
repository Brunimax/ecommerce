<template>
  <v-dialog v-model="dialog" max-width="400px">
    <v-card>
      <v-card-title>
        Mudar Status do Pedido #{{ pedido.id }}
      </v-card-title>

      <v-card-text>
        <v-select
          v-model="novoStatus"
          :items="statusOptions"
          label="Novo Status"
          outlined
        ></v-select>
      </v-card-text>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="blue darken-1" text @click="$emit('fechar')">Cancelar</v-btn>
        <v-btn color="blue darken-1" text @click="salvar">Salvar</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  props: ['pedido'],
  data: () => ({
    novoStatus: '',
    statusOptions: ['CRIADO', 'PROCESSANDO', 'ENVIADO', 'ENTREGUE', 'CANCELADO']
  }),
  computed: {
    dialog: {
      get() { return !!this.pedido },
      set(value) { if (!value) this.$emit('fechar') }
    }
  },
  watch: {
    pedido: {
      immediate: true,
      handler(newVal) {
        if (newVal) {
          this.novoStatus = newVal.status
        }
      }
    }
  },
  methods: {
    salvar() {
      this.$emit('salvar', { 
        ...this.pedido, 
        status: this.novoStatus 
      })
    }
  }
}
</script>
