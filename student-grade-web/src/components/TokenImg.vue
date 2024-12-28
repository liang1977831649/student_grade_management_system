<script setup>
import axios from "axios";
import { onMounted, ref } from 'vue';
import { useToken } from "@/store/token.js"

// Vue.prototype.axios = axios

const token = useToken();
const props = defineProps({
  src: String
});

const tempUrl = ref(null);

// onMounted(async () => {
//   console.log("props.src=",props.src);
//   const response = await axios.get(props.src, {
//     headers: {
//       Authorization: token.token,
//     },
//     responseType: "blob" //将响应直接存储为 Blob 对象
//   });
//   // 通过 axios 请求获取到的图片数据的 Blob 转换为一个临时的 URL
//   tempUrl.value = URL.createObjectURL(response.data);
// });
const loadAvatar = async () => {
  console.log("props.src=", props.src);
  const response = await axios.get(props.src, {
    headers: {
      Authorization: token.token,
    },
    responseType: "blob" //将响应直接存储为 Blob 对象
  });
  // 通过 axios 请求获取到的图片数据的 Blob 转换为一个临时的 URL
  tempUrl.value = URL.createObjectURL(response.data);
}
loadAvatar()
</script>

<template>
  <img :src="tempUrl" />
</template>
