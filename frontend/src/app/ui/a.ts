// este codigo es para ver lo de la subida de archivos

// const [file, setFile] = useState<any>();
// const [formData, setFormData] = useState({
//   name: "",
//   description: "",
// });

// const handleFileChange = (event: any) => {
//   setFile(event.target.files[0]);
// };

// const handleInputChange = (event: any) => {
//   const { name, value } = event.target;
//   setFormData((prevData) => ({
//     ...prevData,
//     [name]: value,
//   }));
// };

// const handleSubmit = async (event: any) => {
//   event.preventDefault();
//   const data = new FormData();
//   data.append("file", file);
//   data.append("json", JSON.stringify(formData));
//   console.log(data);
// };

// <div className="flex flex-col gap-5 w-3/4 bg-red-200 items-center justify-center">
//   <form onSubmit={handleSubmit}>
//     <label className="block text-sm font-medium leading-6 text-gray-900">
//       Name
//     </label>
//     <input
//       type="text"
//       name="name"
//       value={formData.name}
//       onChange={handleInputChange}
//       className="block w-full text-sm text-gray-900 border border-gray-300 rounded-lg cursor-pointer bg-gray-50 focus:outline-none"
//     />

//     <label className="block text-sm font-medium leading-6 text-gray-900">
//       Description
//     </label>
//     <textarea
//       name="description"
//       value={formData.description}
//       onChange={handleInputChange}
//       className="block w-full text-sm text-gray-900 border border-gray-300 rounded-lg cursor-pointer bg-gray-50 focus:outline-none"
//     ></textarea>

//     <label className="block text-sm font-medium leading-6 text-gray-900">
//       Upload File
//     </label>
//     <input
//       type="file"
//       onChange={handleFileChange}
//       className="block w-full text-sm text-gray-900 border border-gray-300 rounded-lg cursor-pointer bg-gray-50 focus:outline-none"
//     />

//     <button
//       type="submit"
//       className="mt-4 px-4 py-2 bg-blue-600 text-white rounded-md"
//     >
//       Upload
//     </button>
//   </form>
// </div>
