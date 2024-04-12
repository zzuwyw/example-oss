import { defineStore } from 'pinia'

const menuStore = defineStore('menu', {
    persist: {
        key: "menu_store",
        storage: localStorage
    },
    state: () => {
        return {
            menuWidth: 260,
            isCollapse: false
        };
    },
    actions: {
        setIsCollapse(isCollapse) {
            this.isCollapse = isCollapse;
            return this.isCollapse;
        },
        setMenuWidth(menuWidth) {
            this.menuWidth = menuWidth;
            return this.menuWidth;
        },
        getters: {}
    }
});

export default menuStore;
