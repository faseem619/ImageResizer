export const PostImage = async (data = {}) => {
  await fetch("https://image-resizer-backend.herokuapp.com/", {
    method: "POST",
    mode: "cors",
    cache: "no-cache",
    credentials: "same-origin",
    redirect: "follow",
    referrerPolicy: "no-referrer",
    body: data,
  });
};

//downloads image from backend
// id used to uniquely identify image record from db
export const GetImage = async (id) => {
  const response = await fetch(
    `https://image-resizer-backend.herokuapp.com/${id}`,
    {
      headers: {
        "Content-Type": "application / octet-stream", // important
        "Content-Disposition": `attachment; filename="compressed.jpg"`,
      },
    }
  );
  const image = await response.blob();
  console.log(image.type);
  let a = document.createElement("a");
  a.href = window.URL.createObjectURL(image);
  a.download = "picture.jpg";
  a.click();
};
