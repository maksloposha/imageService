# Image Search Application

## Description
The **Image Search Application** is a web-based tool that enables users to:
1. Upload images to Amazon S3 cloud storage.
2. View all uploaded images in a gallery.
3. Search for images containing specific objects (e.g., "animal") using **Amazon Rekognition**.

## Technology Stack

### Backend
- **Language:** Java
- **Framework:** Spring Framework
- **Cloud Services:** Amazon S3, Amazon Rekognition
- **Hosting:** Amazon EC2

### Frontend
- **Library:** React
- **Styling:** HTML/CSS
- **HTTP Requests:** Axios
- **Hosting:** Amazon S3 (Static Website Hosting)

## Features
1. **Image Upload:**
   - Users can upload images to an Amazon S3 bucket.
   - The file input field is automatically cleared after a successful upload.

2. **View All Images:**
   - Displays all images from the S3 bucket in a gallery format.

3. **Search Images:**
   - Users can input a keyword to search for images containing specific objects.
   - Uses Amazon Rekognition to detect object labels in images.
   - Displays search results in the "Images with item" section.


## Deployment

### Backend
- The backend is deployed on an Amazon EC2 instance.
- The EC2 instance is configured to automatically restart the backend application after a server reboot.

### Frontend
- The frontend is deployed on Amazon S3 using Static Website Hosting.
- The S3 bucket has a public access policy to serve static files.

## Configuration

### Providing Configuration Options

Before starting the application, you need to provide the following options. These can be set either in a configuration file (`application.properties` or `application.yml`) or through environment variables when running the application:

```properties
amazonProperties.endpointUrl=https://s3.dualstack.${region}.amazonaws.com
amazonProperties.accessKey=${ACCESS_KEY}
amazonProperties.secretKey=${SECRET_KEY}
amazonProperties.bucketName=${BUCKET_NAME}
cors.allowed.origins=${ALLOWED_ORIGINS}
```

Make sure to replace the placeholders with your actual values:

- `${region}`: The AWS region where your S3 bucket is located (e.g., `us-east-1`).
- `${ACCESS_KEY}`: Your AWS Access Key.
- `${SECRET_KEY}`: Your AWS Secret Key.
- `${BUCKET_NAME}`: The name of your S3 bucket.
- `${ALLOWED_ORIGINS}`: A list of allowed CORS origins (e.g., `http://localhost:3000`).

You can set these as environment variables before running the application, like this:

```bash
export AWS_REGION="us-east-1"
export ACCESS_KEY="your-access-key"
export SECRET_KEY="your-secret-key"
export BUCKET_NAME="your-bucket-name"
export ALLOWED_ORIGINS="http://localhost:3000"
```

Alternatively, you can include these variables directly in your `application.properties` or `application.yml`.

## How to Run Locally

### 1. Clone the Repository
```bash
git clone https://github.com/maksloposha/imageService
cd imageService
```

### 2. Start Backend
Make sure the necessary environment variables or configuration properties are set before starting the backend.

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

### 3. Start Frontend
```bash
cd frontend
npm install
npm start
```

### 4. Access the Application
The application will be available at `http://localhost:3000`.

## Dependencies

### Backend
- AWS SDK (S3, Rekognition)
- Spring Boot
- Spring Web
- Lombock

### Frontend
- React
- Axios

## Future Improvements
- Add functionality to delete images from the S3 bucket.
- Implement sorting and filtering options for images (e.g., by date).
- Set up CI/CD pipelines for automated deployments.
