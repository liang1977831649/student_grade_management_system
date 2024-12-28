<template>
    <el-image :imgUrl="authImg" fit="cover" :zoom-rate="1.2" :preview-src-list="srcList"></el-image>
</template>

<script setup name="">
import { useToken } from "@/store/token";
import { ref, watch } from "vue";
// const { proxy } = getCurrentInstance();
const props = defineProps({
    imgUrl: {
        type: String,
        default: '',
    }
});
const authImg = ref('');
const srcList = ref([]);
function getImgUrl() {
    Object.defineProperty(Image.prototype, "imgurl", {
        configurable: true,
        enumerable: true,
        configurable: true
    })
    const request = new XMLHttpRequest();
    request.responseType = 'blob';
    request.open('get', props.imgUrl, true);

    const token = getToken();
    request.setRequestHeader('Authorization', useToken().token);
    request.onreadystatechange = e => {
        if (request.readyState === XMLHttpRequest.DONE && request.status === 200) {
            authImg.value = URL.createObjectURL(request.response)
            srcList.value = []
            srcList.value.push(URL.createObjectURL(request.response))
            // proxy.$refs.authImg.src = URL.createObjectURL(request.response);
            // proxy.$refs.authImg.onload = () => {
            // URL.revokeObjectURL(proxy.$refs.authImg.src);
            // };
        }
    };
    request.send(null);
}
watch(() => props.imgUrl, () => {
    getImgUrl();
})
getImgUrl();
</script>
