import request from "@/utils/request.js";
export const searchServer= async (searchBody)=>{
    return await request.get("/student/list",{
        params:{
            pageNum:searchBody.value.pageNum,
            pageSize:searchBody.value.pageSize,
            name:searchBody.value.name,
            id:searchBody.value.id
        }
     })
}
export const addServer=async(studentModel)=>{
    await request.post("/student",studentModel.value);
}
export const editServer=async(studentModel)=>{
    await request.put("/student",studentModel.value); 
}
export const deleteServer=async(id)=>{
    await request.delete("/student?id="+id);
}

export const updateStudentPasswordServer=async (passwordBody)=>{
    const param=new URLSearchParams;
    console.log("passwordBody.value=",passwordBody.value);
    for(let key in passwordBody.value){
        param.append(key,passwordBody.value[key])
    }
    await request.post('/student/password',param)
}