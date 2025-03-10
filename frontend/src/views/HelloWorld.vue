<template>
  <div>
    <AppHeader />
    <BackgroundWrapper>
      <v-container>
        <h1 class="text-center mb-6">Bem-vindo ao E-Commerce</h1>
        <!-- Conteúdo da página -->
      </v-container>
    </BackgroundWrapper>
    <!-- AppFooter (opcional) -->
  </div>
</template>

<script>
import AppHeader from '@/components/common/AppHeader'
import AppFooter from '@/components/common/AppFooter'
import BackgroundWrapper from '@/components/common/BackgroundWrapper'

export default {
  components: {
    AppHeader,
    AppFooter,
    BackgroundWrapper
  }
}
</script>
