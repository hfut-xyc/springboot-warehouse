import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    username: window.localStorage.getItem("username") == null 
      ? "" : window.localStorage.getItem("username"),
    isRemember: window.localStorage.getItem("isRemember") == null 
    ? false : (window.localStorage.getItem("isRemember") == "true"),
  },
	
  mutations: {
    login(state, data) {
      state.username = data.username;
      state.isRemember = data.checked;
      window.localStorage.setItem("username", data.username);
      window.localStorage.setItem("isRemember", data.checked);
    },
		
		logout(state) {
      if (!state.isRemember) {
        state.username = "";
        window.localStorage.setItem("username", "");
      }
		}
  }
})
