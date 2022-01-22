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
