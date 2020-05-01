import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    username: window.sessionStorage.getItem("username") 
  },
	
  mutations: {
    login(state, username) {
      state.username = username;
      window.sessionStorage.setItem('username', JSON.stringify(username));
    },
		
		logout(state) {
			window.sessionStorage.removeItem('username');
		}
  }
})
