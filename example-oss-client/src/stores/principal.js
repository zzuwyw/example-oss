import { defineStore } from 'pinia'
import { reactive } from "vue";

export const usePrincipalStore = defineStore('principal', () => {
  const principal = reactive({
    userDetail: null
  })

  return { principal }
})
