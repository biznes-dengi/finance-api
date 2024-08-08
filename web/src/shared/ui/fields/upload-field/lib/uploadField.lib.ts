// type UploadPromise<T> = Promise<T> & {abort: () => void};
//
// /**
//  * соединить с функцией, которая знает URL куда отправлять (goalModel.uploadImage())
//  * */
//
// function upload<T>(file: File, url: string, options?: {onProgress?: (progress: number) => void}): UploadPromise<T> {
// 	const {onProgress} = options ?? {};
//
// 	const xhr = new XMLHttpRequest();
// 	xhr.responseType = 'json';
//
// 	const promise = new Promise((resolve, reject) => {
// 		xhr.open('POST', url);
//
// 		xhr.upload.onprogress = (event) => {
// 			// event.total - общий вес файла в байтах, event.loaded - количество загруженных байт, Math.round((event.loaded / event.total) * 100) - вычисление процента в формате от 1 до 100
// 			onProgress?.(Math.round((event.loaded / event.total) * 100));
// 		};
//
// 		xhr.onload = () => {
// 			if (xhr.status === 200) resolve(xhr.response);
// 			else reject(xhr.response);
// 		};
//
// 		const apiKey = 'my_file';
// 		const value = new FormData();
// 		value.append(apiKey, file);
//
// 		xhr.send(value);
// 	}) as UploadPromise<T>;
//
// 	// Присвоили свойство abort, которое прервет запрос, возвращаемому промису, чтобы не возвращать {promise, abort} а только один promise
// 	promise.abort = () => xhr.abort();
//
// 	return promise;
// }
