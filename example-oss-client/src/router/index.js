import { createRouter, createWebHistory } from 'vue-router'
import {usePrincipalStore} from "@/stores/principal.js";
import Layout from "@/components/layout/Index.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'welcome',
      component: () => import('@/views/WelcomeView.vue'),
      children: [
        {
          path: '',
          name: 'login',
          component: () => import('@/components/welcome/Login.vue'),
        }
      ]
    }, {
      path: '/index',
      name: 'index',
      component: Layout,
      children: [
        {
          path: "/home",
          name: "home",
          component: () => import('@/views/Home.vue')
        },
        {
          path: "/me",
          name: "me",
          component: () => import('@/views/me/me.vue')
        }
      ]
    }
  ]
})


router.beforeEach(async (to) => {
  const principalStore = usePrincipalStore();
  const isAuthenticated = !!principalStore.principal.userDetail;

  if (isAuthenticated && to.name === 'login') {
    return '/home';
  }

})


router.beforeEach((to, from, next) => {
  const principalStore = usePrincipalStore();
  const isAuthenticated = !!principalStore.principal.userDetail;
  console.log(principalStore.principal)

  if (isAuthenticated && to.name === 'welcome') {
    next('/home');
  } else if (!isAuthenticated && to.name !== 'login') {
    next('/');
  } else if (to.matched.length === 0) {
    next('/home');
  } else {
    next();
  }
})

export default router
