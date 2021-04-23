import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import installElementPlus from './plugins/element'
import axios from "./global/axiosConfig";
import vueAxios from 'vue-axios'
// console.log(axios);

const app = createApp(App)
installElementPlus(app)
app.use(router).mount('#app')
app.use(vueAxios,axios);