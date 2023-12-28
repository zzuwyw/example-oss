import { createRouter, createWebHistory } from 'vue-router'
import {principalStore} from "@/stores/me.js";

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
      component: () => import('@/views/IndexView.vue'),
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
  const store = principalStore();
  const isAuthenticated = !!store.principal.me;

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
