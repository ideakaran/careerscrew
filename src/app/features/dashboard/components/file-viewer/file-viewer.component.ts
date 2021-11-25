import {Component, Input, OnInit} from '@angular/core';
import {CandidateService} from '@app/core/services/candidate.service';
import {CandidateFile} from '@app/core/model/core.model';
import {ApiEndpoints} from '@app/core/app-url.constant';
import {PDFDocumentProxy} from 'ng2-pdf-viewer';


@Component({
  selector: 'app-file-viewer',
  templateUrl: './file-viewer.component.html',
  styleUrls: ['./file-viewer.component.scss']
})
export class FileViewerComponent implements OnInit {

  @Input() isFetchByFileId = false;
  @Input() candidateJobId: number;

  @Input() fileId: number;
  @Input() width = '100%';
  @Input() height = '100vh';

  isLoading = false;
  isPdfLoading = false;
  hasPdfViewError = false;
  pdfViewErrorMessage: string | null = null;

  fileViewMessage: string | null = null;
  src: object;

  candidateFile: CandidateFile;
  pageVariable = 1;
  zoom = 1;
  angle = 0;
  zoomRate = 0.1;

  constructor(private candidateService: CandidateService) {
  }

  ngOnInit(): void {
    if (this.isFetchByFileId) {
      this.getSrcUrlByFileId();
    } else {
      this.getSrcUrlByJobId();
    }
  }

  getSrcUrlByJobId(): void {
    this.isLoading = true;
    this.candidateService.getCandidateResumeByJobId(this.candidateJobId)
      .subscribe((candidateFile: CandidateFile) => {
        console.log('candidateFile ', candidateFile);
        this.candidateFile = candidateFile;
        this.src = {
          url: ApiEndpoints.CANDIDATE_FILE.DOWNLOAD_CANDIDATE_FILE_BY_ID + '/' + candidateFile.id,
          withCredentials: true
        };
        this.isLoading = false;
        this.isPdfLoading = true;
      }, error => {
        this.isLoading = false;
        this.isPdfLoading = false;
      });
  }

  getSrcUrlByFileId(): void {
  }

  showPdfErrorMessage(): boolean {
    const showPdfErrorMessage = !this.isPdfLoading && this.hasPdfViewError &&
      (this.pdfViewErrorMessage && this.pdfViewErrorMessage.length > 0 ||
        this.fileViewMessage && this.fileViewMessage.length > 0);
    return showPdfErrorMessage;
  }

  // afterLoadComplete
  callBackFn(pdf: PDFDocumentProxy): void {
    // do anything with "pdf"
    this.isPdfLoading = false;
    this.hasPdfViewError = false;
    this.fileViewMessage = null;
    this.pdfViewErrorMessage = null;
  }

  onErrorFn($event: any): void {
    if ($event && $event.status) {
      this.fileViewMessage = $event.message || 'Something went wrong, Cant load requested file !!!.';
      this.hasPdfViewError = true;
      this.pdfViewErrorMessage = 'File Couldn\'t be viewed. But you can still attempt to download the file.';
      this.isPdfLoading = false;
    }
  }

  // Actions
  plusZoom(): void {
    this.zoom = this.zoom + this.zoomRate;
  }

  minusZoom(): void {
    if (this.zoom > 1) {
      this.zoom = this.zoom - this.zoomRate;
    }
  }

  rotate(): void {
    if (this.angle === 0) {
      this.angle = 90;
    } else if (this.angle === 90) {
      this.angle = 180;
    } else if (this.angle === 180) {
      this.angle = 270;
    } else if (this.angle === 270) {
      this.angle = 0;
    }
  }


  nextPage(): void {
    this.pageVariable++;
  }

  previousPage(): void {
    if (this.pageVariable > 1) {
      this.pageVariable--;
    }
  }

  downloadResource(openInTab: boolean = false): void {
    this.candidateService.downloadCandidateFileByFileId(this.candidateFile.id).subscribe(response => {
      console.log('file from server ', response);
      const dataType = response.type;
      const binaryData = [];
      binaryData.push(response);
      if (openInTab) {
        const fileURL = URL.createObjectURL(new Blob(binaryData, {type: dataType}));
        window.open(fileURL, '_blank');
      } else {
        const downloadLink = document.createElement('a');
        downloadLink.href = window.URL.createObjectURL(new Blob(binaryData, {type: dataType}));
        if (this.candidateFile.fileName) {
          downloadLink.setAttribute('download', this.candidateFile.fileName);
        }
        document.body.appendChild(downloadLink);
        downloadLink.click();
      }
    });

  }

}
