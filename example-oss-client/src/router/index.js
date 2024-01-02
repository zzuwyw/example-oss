import { createRouter, createWebHistory } from 'vue-router'
import {principalStore} from "@/stores/me.js";
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
      //component: () => import('@/views/IndexView.vue'),
    }
  ]
})


router.beforeEach(async (to, from) => {
  const store = principalStore();
  const isAuthenticated = !!store.principal.me;

  if (isAuthenticated && to.name === 'login') {
    return '/index';
  }

})


router.beforeEach((to, from, next) => {
  const authenticateStore = principalStore();
  const isAuthenticated = !!authenticateStore.principal.me;

  if (isAuthenticated && to.name === 'welcome') {
    next('/index');
  } else if (!isAuthenticated && to.name !== 'login') {
    next('/');
  } else if (to.matched.length === 0) {
    next('/index');
  } else {
    next();
  }
})

export default router
