import Vue from 'vue'
import VueRouter from 'vue-router'
import HomePage from '@/views/HomePage.vue'
import ProdutosPage from '@/views/ProdutosPage.vue'

Vue.use(VueRouter)

const routes = [
  { path: '/', name: 'HomePage', component: HomePage },
  { path: '/produtos', name: 'ProdutosPage', component: ProdutosPage }
]

export default new VueRouter({
  mode: 'history',
  routes
})
