export const PostImage = async (data = {}) => {
  await fetch("http://localhost:8080", {
    method: "POST",
    mode: "cors",
    cache: "no-cache",
    credentials: "same-origin",
    redirect: "follow",
    referrerPolicy: "no-referrer",
    body: data,
  });
};

export const GetImage = async (id) => {
  console.log(id);
  const response = await fetch(`http://localhost:8080/${id}`, {
    headers: {
      "Content-Type": "application / octet-stream",
      "Content-Disposition": `attachment; filename="compressed.jpg"`,
    },
  });
  const image = await response.blob();
  let a = document.createElement("a");
  a.href = window.URL.createObjectURL(image);
  a.download = "picture.jpg";
  a.click();
};
