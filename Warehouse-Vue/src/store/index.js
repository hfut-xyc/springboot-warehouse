import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    username: window.localStorage.getItem("username") == null 
      ? "" : window.localStorage.getItem("username"),
    is_remember: window.localStorage.getItem("is_remember") == null 
    ? false : (window.localStorage.getItem("is_remember") == "true"),
  },
	
  mutations: {
    login(state, data) {
      state.username = data.username;
      state.is_remember = data.checked;
      window.localStorage.setItem("username", data.username);
      window.localStorage.setItem("is_remember", data.checked);
    },
		
		logout(state) {
      if (!state.is_remember) {
        state.username = "";
        window.localStorage.setItem("username", "");
      }
		}
  }
})
