import {defineStore} from 'pinia'
import {reactive} from "vue";

export const principalStore = defineStore('me', () => {
  const principal = reactive({
    me: null
  })

  return { principal }
})
