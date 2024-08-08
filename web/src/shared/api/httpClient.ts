export class HttpClient {
	static get() {
		return;
	}
}

/** Notes
 const axiosInstance = axios.create({
 baseURL: 'http://127.0.0.1:5000/api/v1/',
 headers: {
 'Content-Type': 'application/json',
 // authorization: `Bearer ${AuthService.accessToken}`,
 },
 withCredentials: true,
 });

 static post<P>(url: string, payload: P) {
 return axiosInstance.post(url, payload).then(handleGeneralPromiseResolve).catch(handleGeneralPromiseReject);
 }

 function handleGeneralPromiseResolve<T>(response: AxiosResponse<T>) {
 //add general error handling, 401, 403, error page
 return response.data;
 }

 function handleGeneralPromiseReject(error: AxiosError) {
 return Promise.reject(error);
 }

 const setTokenToInstance = (token: string) => {
 localStorage.setItem('token', token);

 axiosInstance.interceptors.request.use((lib) => {
 lib.headers.authorization = localStorage.getItem('token');

 return lib;
 });
 };

 */
