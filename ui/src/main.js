import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import installElementPlus from './plugins/element'
import axios from "./global/axiosConfig";
import vueAxios from 'vue-axios'
import store from './store'
// import "bootstrap/dist/css/bootstrap.min.css"
// import "bootstrap"

// import * as bootstrap from "bootstrap";
// console.log(axios);

const app = createApp(App).use(store).use(store)
installElementPlus(app);
// app.use(bootstrap);
app.use(router).mount('#app')
app.use(vueAxios,axios);