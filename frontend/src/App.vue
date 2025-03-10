<template>
  <v-app>
    <!-- Cabeçalho -->
    <AppHeader />
    
    <!-- Conteúdo Principal -->
    <v-main>
      <BackgroundWrapper>
        <router-view />
      </BackgroundWrapper>
    </v-main>
  </v-app>
</template>

<script>
import AppHeader from './components/common/AppHeader'
import BackgroundWrapper from './components/common/BackgroundWrapper'

export default {
  components: {
    AppHeader,
    BackgroundWrapper
  }
}
</script>

<style>
/* Estilos globais podem ser adicionados aqui */
body {
  margin: 0;
  font-family: 'Roboto', sans-serif;
}
</style>
