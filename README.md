# file-encode-decode
## File Encode and Decode Service

This project provides a Spring Boot application for encoding and decoding files to and from Base64. It includes two main functionalities:

1. **Encode a file to Base64**: Upload a file and get its Base64 encoded string.
2. **Decode a Base64 string to a file**: Provide a Base64 string to decode it back to the original file.

## Features

- **File Upload**: Allows users to upload files (e.g., images).
- **Base64 Encoding**: Encodes uploaded files into Base64 strings.
- **Base64 Decoding**: Decodes a Base64 string back into a file.
- **Content Type Detection**: Automatically detects and returns the content type (e.g., JPEG, PNG, GIF) of the decoded file.

## Prerequisites

- Java 21 or higher
- Docker (optional for containerization)
- Spring Boot 3.4.2

## Project Structure

- `FileEncodeDecodeApplication`: The main entry point of the application.
- `FileController`: Handles the encoding and decoding requests.
  - **/api/files/encode-file**: Endpoint for encoding files to Base64.
  - **/api/files/decode-file**: Endpoint for decoding Base64 strings into files.
- `application.properties`: Configuration file for setting the application port and logging settings.

## Setup

1. **Clone the repository**:

    ```bash
    git clone https://github.com/vaibhu9/file-encode-decode.git
    cd file-encode-decode
    ```

2. **Build and Run the Application**:

    If you want to run the application locally, use the following commands:

    ```bash
    ./gradlew bootRun
    ```
    The application will start on http://localhost:9090/com.amazingcode.in.

3. **Docker (optional)**:

    To build and push the Docker image, you can use the GitHub Actions workflow or manually run the following commands:

    ```bash
    docker build -t file-encode-decode .
    docker run --name file-encode-decode -p 9090:9090 file-encode-decode
    ```

4. **Access the API**:

    Encode a File to Base64:

    - **Endpoint**: `/api/files/encode-file`
    - **Method**: `POST`
    - **Parameters**: `file` (multipart file)
    - **Response**: Base64 encoded string

    Decode a Base64 String to File:

    - **Endpoint**: `/api/files/decode-file`
    - **Method**: `POST`
    - **Parameters**: `base64` (Base64 encoded string)
    - **Response**: The decoded file with appropriate content type (e.g., JPEG, PNG, GIF)

## Example Usage

-   **Encode a file to Base64**:
    
    Request:
    
    ```bash
    POST http://localhost:9090/com.amazingcode.in/api/files/encode-file
    Content-Type: multipart/form-data
    file: <your-file>
    ```

    Response:

    ```json
    {
        "base64": "<Base64-encoded-file-string>"
    }
    ```

-   **Decode a Base64 string to a file**:
    
    Request:
    
    ```bash
    POST http://localhost:9090/com.amazingcode.in/api/files/decode-file
    Content-Type: application/json
    {
        "base64": "<Base64-encoded-file-string>"
    }
    ```

    Response:
    
    The decoded file will be returned with the correct content type (e.g., `image/jpeg`, `image/png`, `image/gif`).

## Configuration

-   **Server Port**: The server runs on port `9090`. You can change this in the `application.properties` file.
-   **Logging**: Logs are configured to output to both the console and a log file (`application.log`).

## Docker Image
The project includes a GitHub Action to automatically build and push a Docker image to Docker Hub. The image is tagged with the latest version and a version-specific tag.

## Contributing
Feel free to open issues or submit pull requests. Contributions are welcome!

<pre style="overflow-x: auto; white-space: nowrap;">
This README file outlines the project features, setup instructions, API usage, and Docker instructions. Adjust the Docker image tags and other information based on your actual setup and repository details.
</pre>