import { createRouter, createWebHistory } from 'vue-router'

import DashboardView from './views/DashboardView.vue'
import RequestsView from './views/RequestsView.vue'
import RequestDetailsView from './views/RequestDetailsView.vue'
import AboutView from './views/AboutView.vue'

export default createRouter({
  history: createWebHistory(),

  routes: [

    {
      path: '/',
      redirect: '/applications'
    },

    {
      path: '/dashboard',
      name: 'dashboard',
      component: DashboardView
    },

    {
      path: '/applications',
      name: 'applications',
      component: RequestsView,
    },

    {
      path: '/applications/:id',
      name: 'application-details',
      component: RequestDetailsView,
      props: true 
    },

    {
      path: '/about',
      name: 'about',
      component: AboutView
    }

  ]
})