import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    currentUser: window.sessionStorage.getItem("currentUser")
  },
	
  mutations: {
    login(state, username) {
      state.currentUser = username;
      window.sessionStorage.setItem("currentUser", username);
    },
		
		logout(state) {
      window.sessionStorage.removeItem("currentUser");
		}
  }
})
